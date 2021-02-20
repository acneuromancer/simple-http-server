package com.codefromscratch.httpserver.config;

import com.codefromscratch.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    /* Use to load a configuration file by the path provided */
    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttPConfigurationException(e);
        }

        StringBuffer sb = new StringBuffer();

        int i;
        while(true) {
            try {
                if (!((i = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                throw new HttPConfigurationException(e);
            }
            sb.append((char) i);
        }

        JsonNode conf = null;

        try {
            conf = Json.parse(sb.toString());
        } catch (JsonProcessingException e) {
            throw new HttPConfigurationException("Error parsing the configuration file.", e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttPConfigurationException("Error parsing the configuration file, internal.", e);
        }
    }

    /* Returns the current loaded configuration */
    public Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            throw new HttPConfigurationException("No current configuration set.");
        }

        return myCurrentConfiguration;
    }
}
