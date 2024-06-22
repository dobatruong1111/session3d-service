package com.saolasoft.websocket.module.comand.service;

import com.saolasoft.websocket.module.comand.service.dto.Parameter;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@Service("commandService")
public class CommandServiceImpl implements CommandService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String command;

    public CommandServiceImpl(@Value("${application.setting.command}") String command) {
        this.command = command;
    }


    @Override
    public boolean execute(String command) {
        // Logic thực thi câu lệnh start vtk 3d server
        return process(command);
    }

    @Override
    public boolean execute(Map<Parameter, String> parameters) {
        // Logic thực thi câu lệnh start vtk 3d server
        StringSubstitutor substitutor = new StringSubstitutor(parameters.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getValue(),
                        Map.Entry::getValue
                )));

        String executeCommand = substitutor.replace(command);
        return process(executeCommand);
    }

    private boolean process(String command) {
        ProcessBuilder builder = new ProcessBuilder();

        // Nếu lệnh chạy VTK trên win khác với linux
        // Check OS là win thì dùng command của win
        // Còn linux thì dùng của linux


        builder.command(command);

        try {

            Process process = builder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();

            if (exitVal == 0) {
                // Success
                // Check giá trị nào để biết start thành công

                return true;
            } else {
                //
            }
            return false;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            // Lam gi do
            return false;
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            // Lam gi do
            return false;
        }

    }
    private void processWindowCommand() {

    }
    private void processUnixCommand() {

    }
}
