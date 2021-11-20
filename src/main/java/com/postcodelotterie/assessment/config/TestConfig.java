
package com.postcodelotterie.assessment.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.postcodelotterie.assessment.repository", "com.postcodelotterie.assessment.controller"})
public class TestConfig {

}
