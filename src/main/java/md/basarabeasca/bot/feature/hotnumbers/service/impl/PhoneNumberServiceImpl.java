package md.basarabeasca.bot.feature.hotnumbers.service.impl;

import lombok.AllArgsConstructor;
import md.basarabeasca.bot.feature.hotnumbers.dao.model.PhoneNumber;
import md.basarabeasca.bot.feature.hotnumbers.dao.repository.PhoneNumberRepository;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;
import md.basarabeasca.bot.feature.hotnumbers.service.PhoneNumberService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static md.basarabeasca.bot.settings.StringUtil.ERROR;
import static md.basarabeasca.bot.settings.StringUtil.NUMBER_WAS_ADDED;
import static md.basarabeasca.bot.settings.StringUtil.NUMBER_WAS_DELETED;

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
    public List<PhoneNumber> getNextPage(Long lastId) {
        return new ArrayList<>(phoneNumberRepository.getNextPage(lastId));
    }

    @Override
    public List<PhoneNumber> getPreviousPage(Long lastId) {
        return new ArrayList<>(phoneNumberRepository.getPreviousPage(lastId));
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
            return ERROR;
        }

        return NUMBER_WAS_ADDED;
    }

    @Override
    public String deleteNumber(String number) {
        try {
            phoneNumberRepository.delete(phoneNumberRepository.findByNumber(number));
        } catch (Exception ex) {
            return ERROR;
        }
        return NUMBER_WAS_DELETED;
    }

    @Override
    public List<PhoneNumberDto> findByDescription(String description) {
        try {
            return phoneNumberRepository.findByDescriptionIgnoreCaseContaining(description)
                    .stream()
                    .map(this::convertToDTO)
                    .sorted(Comparator.comparing(PhoneNumberDto::getDescription))
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            return null;
        }
    }

    public PhoneNumberDto convertToDTO(PhoneNumber phoneNumber) {
        return modelMapper.map(phoneNumber, PhoneNumberDto.class);
    }
}
