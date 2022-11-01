package ru.gb.homework11.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBoxGroup {
    private boolean userRole;
    private boolean managerRole;
    private boolean adminRole;
    private boolean rootRole;
}
