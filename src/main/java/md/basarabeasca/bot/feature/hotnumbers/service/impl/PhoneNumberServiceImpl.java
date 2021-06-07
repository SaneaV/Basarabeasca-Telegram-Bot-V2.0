package md.basarabeasca.bot.feature.hotnumbers.service.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.feature.hotnumbers.dao.model.PhoneNumber;
import md.basarabeasca.bot.feature.hotnumbers.dao.repository.PhoneNumberRepository;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;
import md.basarabeasca.bot.feature.hotnumbers.service.PhoneNumberService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PhoneNumberDto> getAllNumbers() {
        return phoneNumberRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(PhoneNumberDto::getDescription))
                .collect(Collectors.toList());
    }

    @Override
    public String addNumber(String number, String description) {
        PhoneNumber phoneNumber = PhoneNumber.builder()
                .number(number)
                .description(description)
                .build();

        try {
            phoneNumberRepository.save(phoneNumber);
        } catch (Exception ex) {
            return "Ошибка!";
        }

        return "Номер был добавлен";
    }

    @Override
    public String deleteNumber(String number) {
        try {
            phoneNumberRepository.delete(phoneNumberRepository.findByNumber(number));
        } catch (Exception ex) {
            return "Ошибка!";
        }
        return "Номер был удалён";
    }

    public PhoneNumberDto convertToDTO(PhoneNumber phoneNumber) {
        return modelMapper.map(phoneNumber, PhoneNumberDto.class);
    }
}