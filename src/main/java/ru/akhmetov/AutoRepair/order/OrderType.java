package ru.akhmetov.AutoRepair.order;

/**  Способ хранения рыночных ордеров в базе Ordinal **/
//Способ хранения в базе Ordinal, не меняй порядок enum!
public enum OrderType {
    cheque, //Запчасти по чеку
    work    //Произведенная работа
}
