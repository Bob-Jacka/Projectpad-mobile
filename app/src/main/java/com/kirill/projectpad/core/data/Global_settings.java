package com.kirill.projectpad.core.data;

import com.kirill.projectpad.core.data.entities.Project_data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for global data
 */
public abstract class Global_settings {

    public static List<Project_data> projects = new ArrayList<>();
    public static final String SAVE_FILE_NAME = "/saveProjects";
    public static String full_path_to_save;

    public static int today = LocalDateTime.now().getDayOfYear();

    public static final String empty_line = "0";

    public static final String serializeKeyProject = "projectToView";
}
