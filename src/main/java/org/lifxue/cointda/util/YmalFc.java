/*
 * Copyright 2019 xuelf.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lifxue.cointda.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lifxue.cointda.bean.Cryptocurrency;

/**
 *
 * @author xuelf
 */
public class YmalFc<T> {

    private static final Logger logger = LogManager.getLogger(YmalFc.class.getName());
    private final YAMLFactory yamlFactory;
    private final ObjectMapper mapper;
    private final Class<T> klass;

    public YmalFc(Class<T> klass) {
        this.klass = klass;
        this.yamlFactory = new YAMLFactory();
        this.mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    }

    public T build(String path) {
        T config = null;
        try {
            InputStream input =new FileInputStream(path);
            //InputStream input = YmalFc.class.getResourceAsStream(path);
            YAMLParser yamlParser = yamlFactory.createParser(input);
            final JsonNode node = mapper.readTree(yamlParser);
            TreeTraversingParser treeTraversingParser = new TreeTraversingParser(node);
            config = mapper.readValue(treeTraversingParser, klass);
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return config;
    }

    public T build() {
        String filePath = System.getProperty("user.dir");
        return build(filePath + "/conf/Cryptocurrency.yml");
    }

    public static void main(String[] args) {
        Cryptocurrency contact = new Cryptocurrency();
        YmalFc<Cryptocurrency> ymalFc = new YmalFc<Cryptocurrency>(Cryptocurrency.class);

         String filePath = System.getProperty("user.dir");
        contact = ymalFc.build(filePath + "/conf/Cryptocurrency.yml");

    }
}
