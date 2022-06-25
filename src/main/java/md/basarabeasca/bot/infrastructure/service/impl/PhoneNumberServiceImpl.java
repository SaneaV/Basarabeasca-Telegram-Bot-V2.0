package md.basarabeasca.bot.infrastructure.service.impl;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.dao.domain.PhoneNumber;
import md.basarabeasca.bot.dao.mapper.PhoneNumberMapper;
import md.basarabeasca.bot.dao.repository.PhoneNumberRepository;
import md.basarabeasca.bot.dao.repository.model.PhoneNumberJpa;
import md.basarabeasca.bot.infrastructure.service.PhoneNumberService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberServiceImpl implements PhoneNumberService {

  private static final String NUMBER_WAS_ADDED = "Номер был добавлен";
  private static final String NUMBER_WAS_DELETED = "Номер был удалён";

  private final PhoneNumberRepository phoneNumberRepository;
  private final PhoneNumberMapper phoneNumberMapper;

  @Override
  public List<PhoneNumber> getNextPage(Long lastId) {
    return phoneNumberRepository.findTop10ByIdGreaterThan(lastId).stream()
        .map(phoneNumberMapper::toEntity)
        .collect(toList());
  }

  @Override
  public List<PhoneNumber> getPreviousPage(Long lastId) {
    return phoneNumberRepository.findTop10ByIdLessThanEqualOrderByIdDesc(lastId).stream()
        .sorted(comparing(PhoneNumberJpa::getId))
        .map(phoneNumberMapper::toEntity)
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
  public List<PhoneNumber> findByDescription(String description) {
    try {
      return phoneNumberRepository.findByDescriptionIgnoreCaseContaining(description)
          .stream()
          .map(phoneNumberMapper::toEntity)
          .sorted(comparing(PhoneNumber::getDescription))
          .collect(toList());
    } catch (Exception exception) {
      return null;
    }
  }

  @Override
  public Long getMaxIdOnPage(Long lastId) {
    return phoneNumberRepository.getMaxIdOnPage(lastId);
  }

  @Override
  public Long getMinIdOnPage(Long lastId) {
    return phoneNumberRepository.getMinIdOnPage(lastId);
  }

  @Override
  public Long getLastId() {
    return phoneNumberRepository.findFirstIdByOrderByIdDesc().getId();
  }

  @Override
  public Long getFirstId() {
    return phoneNumberRepository.findFirstIdByOrderByIdAsc().getId();
  }
}
