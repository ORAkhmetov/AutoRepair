package ru.akhmetov.AutoRepair.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;

import ru.akhmetov.AutoRepair.appeal.AppealsMapper;
import ru.akhmetov.AutoRepair.appeal.AppealsService;
import ru.akhmetov.AutoRepair.car.CarsMapper;
import ru.akhmetov.AutoRepair.car.CarsService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ClientsController.class)
class ClientsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClientsService clientsService;

    @MockBean
    CarsService carsService;

    @MockBean
    AppealsService appealsService;

    @MockBean
    ClientValidator clientValidator;

    @MockBean
    CarsMapper carsMapper;

    @MockBean
    ClientsMapper clientsMapper;

    @MockBean
    AppealsMapper appealsMapper;

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Должен отдать страницу списка клиентов без пагинации")
    void testIndexWithoutPageReturnsClientsList() throws Exception {
        // given
        var client = new Client();
        client.setId(1);
        client.setFullName("Иванов Иван Иванович");

        // and
        when(clientsService.findAll()).thenReturn(List.of(client));
        when(clientsMapper.convertToClientDTO(client)).thenReturn(new ClientDTO(client));

        // when
        var result = mockMvc.perform(get("/clients"));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("clients/index"))
                .andExpect(model().attributeExists("clients"));

        // and
        verify(clientsService, never()).findWithPagination(any());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Должен отдать страницу списка клиентов с пагинацией, если передан параметр page")
    void testIndexWithPageReturnsPaginatedClientsList() throws Exception {
        // given
        when(clientsService.findWithPagination(0)).thenReturn(Collections.emptyList());

        // when
        var result = mockMvc.perform(get("/clients").param("page", "0"));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("clients/index"));

        // and
        verify(clientsService, never()).findAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Должен отдать карточку клиента с его машинами и обращениями")
    void testShowReturnsClientCard() throws Exception {
        // given
        var client = new Client();
        client.setId(1);
        client.setFullName("Иванов Иван Иванович");

        // and
        when(clientsService.findOne(1)).thenReturn(client);
        when(clientsMapper.convertToClientDTO(client)).thenReturn(new ClientDTO(client));
        when(carsService.getCarsByClient(client)).thenReturn(Collections.emptyList());
        when(appealsService.getAppealsByClient(client)).thenReturn(Collections.emptyList());

        // when
        var result = mockMvc.perform(get("/clients/1"));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("clients/show"))
                .andExpect(model().attributeExists("client", "cars", "appeal"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Должен сохранить клиента и перейти на список, если валидация прошла успешно")
    void testCreateWithValidClientSavesAndRedirects() throws Exception {
        // given
        var client = new Client();
        client.setFullName("Петров Петр Петрович");

        // and
        when(clientsMapper.convertToClient(any(ClientDTO.class))).thenReturn(client);

        // when
        var result = mockMvc.perform(post("/clients")
                .with(csrf())
                .param("fullName", "Петров Петр Петрович"));

        // then
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clients?page=0"));

        // and
        verify(clientsService, times(1)).save(client);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Не должен сохранить клиента и должен вернуть форму, если валидатор нашёл ошибку")
    void testCreateWithValidatorErrorReturnsFormWithoutSaving() throws Exception {
        // given
        var client = new Client();
        client.setFullName("Петров Петр Петрович");

        // and
        when(clientsMapper.convertToClient(any(ClientDTO.class))).thenReturn(client);
        doAnswer(invocation -> {
            Errors errors = invocation.getArgument(1);
            errors.rejectValue("fullName", "", "Клиент с таким именем уже существует");
            return null;
        }).when(clientValidator).validate(any(), any(Errors.class));

        // when
        var result = mockMvc.perform(post("/clients")
                .with(csrf())
                .param("fullName", "Петров Петр Петрович"));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("clients/new"));

        // and
        verify(clientsService, never()).save(any());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Не должен сохранить клиента, если fullName пустой (bean validation)")
    void testCreateWithBlankFullNameReturnsFormWithoutSaving() throws Exception {
        // when
        var result = mockMvc.perform(post("/clients")
                .with(csrf())
                .param("fullName", ""));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("clients/new"));

        // and
        verify(clientsService, never()).save(any());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Должен обновить клиента и перейти на список по идентификатору")
    void testUpdateWithValidClientUpdatesAndRedirects() throws Exception {
        // given
        var client = new Client();
        client.setFullName("Сидоров Сидор Сидорович");

        // and
        when(clientsMapper.convertToClient(any(ClientDTO.class))).thenReturn(client);

        // when
        var result = mockMvc.perform(post("/clients/1")
                .with(csrf())
                .param("_method", "patch")
                .param("fullName", "Сидоров Сидор Сидорович"));

        // then
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clients?page=0"));

        // and
        var idCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(clientsService, times(1)).update(idCaptor.capture(), any(Client.class));
        assertThat(idCaptor.getValue()).isEqualTo(1);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Должен удалить клиента и перейти на список")
    void testDeleteRedirectsToClientsList() throws Exception {
        // when
        var result = mockMvc.perform(post("/clients/1")
                .with(csrf())
                .param("_method", "delete"));

        // then
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clients"));

        // and
        verify(clientsService, times(1)).delete(1);
    }

    @Test
    @DisplayName("Не должен пускать неаутентифицированного пользователя к списку клиентов")
    void testIndexWithoutAuthenticationIsRejected() throws Exception {
        // when
        var result = mockMvc.perform(get("/clients"));

        // then
        result.andExpect(status().isUnauthorized());

        // and
        verify(clientsService, never()).findAll();
    }
}
