package com.company;

import java.io.*;
import java.util.*;

/**
 * Принцип работы:
 * Из поступившего файла получаем названия заголовков,
 * циклом проходим по всем значениям в файле и добавляем в Set заголовок и значение (т.к. каждое значение по индексу соответсвует заголовку).
 * Далее создается файл, у которого в качестве имени заголовок и файл заполняется значениями.
 * Если следующий поток встречает заголовок, на основе которого раннее был создан файл,
 * то из раннее созданого файлы парсятся значения и сливаются с новыми, тем самым получаем уникальность значений.
 */

public class HandlerFiles extends Thread{

    private File inputFile;
    private File outputFile;
    public static final Set<String> resultSet = new TreeSet<>();

    public HandlerFiles(File file){
        this.inputFile = file;
    }

    @Override
    public void run(){
        this.Processing();
    }

    public void Processing(){
        try {
            BufferedReader brF = new BufferedReader(new FileReader(inputFile));
            String line = brF.readLine();
            String[] nameValue = line.split(";");
            brF.close();
            for (int i = 0; i < nameValue.length; i++) {
                synchronized (HandlerFiles.class) {
                    BufferedReader brS = new BufferedReader(new FileReader(inputFile));
                    line = brS.readLine();
                    line = brS.readLine();
                    while (line != null) {
                        String[] valueArray = line.split(";");
                        resultSet.add(valueArray[i]);
                        line = brS.readLine();
                    }
                    brS.close();
                    System.out.println(currentThread().getName() + " " + resultSet);
                    outputFile = new File("./OutputFiles/" + nameValue[i]);
                    if (outputFile.exists()) {
                        BufferedReader br = new BufferedReader(new FileReader(outputFile));
                        String[] value = br.readLine().split(";");
                        resultSet.addAll(Arrays.asList(value));
                    }
                    FileWriter fw = new FileWriter(outputFile);
                    StringBuffer sb = new StringBuffer();
                    Iterator<String> iterator = resultSet.iterator();
                    while(iterator.hasNext()){
                        sb.append(iterator.next().concat(";"));
                    }
                    fw.write(String.valueOf(sb));
                    fw.close();
                    resultSet.clear();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
