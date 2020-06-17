package net.smart.mobcoins.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public enum MessagesEnum {

    TESTE;

    @Getter private String msg;
    @Getter private List<String> list;
}
