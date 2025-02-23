package com.example.sistemaweb.sistemaweb.Entities;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeerExcel {

    public static void main(String[] args) {
        List<String> estudiantesList = new ArrayList<>();
        List<String> estudiantesAgregarList = new ArrayList<>();

        try {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\edwin\\Downloads\\Libro1.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String estudiante = getCellValueAsString(row.getCell(2));
                    estudiantesList.add(estudiante);
                }
            }

            workbook.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] estudiantesArray = estudiantesList.toArray(new String[0]);

        for (String estudiante : estudiantesArray) {
            int spaces = getSpaces(estudiante);
            String[] partes = estudiante.trim().split(" ");

            String apellidoPaterno = "";
            String apellidoMaterno = "";
            String nombres = "";

            if (spaces == 1) {
                apellidoPaterno = partes[0];
                apellidoMaterno = partes[1];
            } else if (spaces == 2) {
                apellidoPaterno = partes[0];
                apellidoMaterno = partes[1];
                nombres = partes[2];
            } else if (spaces == 3) {
                apellidoPaterno = partes[0];
                apellidoMaterno = partes[1];
                nombres = partes[2] + " " + partes[3];
            } else {
                apellidoPaterno = partes[0];
                apellidoMaterno = partes.length > 2 ? partes[1] : "";
                nombres = String.join(" ", Arrays.copyOfRange(partes, 2, partes.length));
            }

            String estudianteFormateado = "Nombre: " + nombres + ", Apellido Paterno: " + apellidoPaterno + ", Apellido Materno: " + apellidoMaterno;
            estudiantesAgregarList.add(estudianteFormateado);

            //System.out.println(estudianteFormateado);
        }

        // Mostrar la lista completa de estudiantes formateados
        System.out.println("Estudiantes Formateados: " + estudiantesAgregarList);
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return String.format("%.0f", cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private static int getSpaces(String s) {
        String[] _name = s.trim().split(" ");
        return _name.length - 1;
    }
}
