package md.basarabeasca.bot.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.domain.repository.PhoneNumberRepository;
import md.basarabeasca.bot.domain.repository.model.PhoneNumberJpa;
import md.basarabeasca.bot.service.PhoneNumberService;
import md.basarabeasca.bot.web.converter.PhoneNumberMapper;
import md.basarabeasca.bot.web.dto.PhoneNumberDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberServiceImpl implements PhoneNumberService {

  private static final String NUMBER_WAS_ADDED = "Номер был добавлен";
  private static final String NUMBER_WAS_DELETED = "Номер был удалён";

  private final PhoneNumberRepository phoneNumberRepository;
  private final PhoneNumberMapper phoneNumberMapper;

  @Override
  public List<PhoneNumberDto> getNextPage(Long lastId) {
    return phoneNumberRepository.getNextPage(lastId).stream()
        .map(phoneNumberMapper::toDto)
        .collect(toList());
  }

  @Override
  public List<PhoneNumberDto> getPreviousPage(Long lastId) {
    return phoneNumberRepository.getPreviousPage(lastId).stream()
        .map(phoneNumberMapper::toDto)
        .collect(toList());
  }

  @Override
  public String addNumber(String number, String description) {
    final PhoneNumberJpa phoneNumberJpa = PhoneNumberJpa.builder()
        .phoneNumber(number)
        .description(description)
        .build();

    try {
      phoneNumberRepository.save(phoneNumberJpa);
      return NUMBER_WAS_ADDED;
    } catch (Exception exception) {
      throw new RuntimeException();
    }
  }

  @Override
  public String deleteNumber(String number) {
    try {
      phoneNumberRepository.delete(phoneNumberRepository.findByPhoneNumber(number));
      return NUMBER_WAS_DELETED;
    } catch (Exception exception) {
      throw new RuntimeException();
    }
  }

  @Override
  public List<PhoneNumberDto> findByDescription(String description) {
    try {
      return phoneNumberRepository.findByDescriptionIgnoreCaseContaining(description)
          .stream()
          .map(phoneNumberMapper::toDto)
          .sorted(Comparator.comparing(PhoneNumberDto::getDescription))
          .collect(toList());
    } catch (Exception exception) {
      return null;
    }
  }
}
