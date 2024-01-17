package ru.akhmetov.AutoRepair.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
/**  Сообщение, гласящее только админ?!  **/

@Service
public class AdminService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }
}
