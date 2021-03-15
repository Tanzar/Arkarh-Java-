/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh;

import com.tanzar.Arkarh.Elements.Assets;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Tanzar
 */
@Configuration
public class AppConfig {
    
    @Bean(name = "assets")
    public Assets assets() throws IOException{
        return new Assets();
    }
}
