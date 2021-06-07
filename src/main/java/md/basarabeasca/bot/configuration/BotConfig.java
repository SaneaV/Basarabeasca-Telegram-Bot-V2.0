package md.basarabeasca.bot.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
