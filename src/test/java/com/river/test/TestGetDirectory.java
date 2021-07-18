package com.river.test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestGetDirectory {

    public static void main(String[] args) {
        Path currentWorkingDir = Paths.get("");
        System.out.println(currentWorkingDir.toAbsolutePath());
    }
}
