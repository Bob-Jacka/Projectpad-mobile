package com.kirill.projectpad.core.data;

import com.kirill.projectpad.core.entities.Project_data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for global data
 */
public abstract class Global_settings {

    public static List<Project_data> projects = new ArrayList<>();
    public static final String SAVE_FILE_NAME = "/saveTasks";
    public static int today = LocalDateTime.now().getDayOfYear();

    public static final String empty_line = "0";

    public static final String serializeKeyBook = "projectToView";
}
