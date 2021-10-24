package com.fizbiz.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverallCapital {

    private AdminList adminList;

    private UserList userList;
}
