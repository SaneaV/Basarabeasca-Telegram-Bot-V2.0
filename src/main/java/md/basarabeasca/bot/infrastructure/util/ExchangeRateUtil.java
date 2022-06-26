package md.basarabeasca.bot.infrastructure.util;

import static lombok.AccessLevel.PRIVATE;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = PRIVATE)
public class ExchangeRateUtil {

  public static final String BNM = "BNM";
  public static final String MOLDINDCONBANK = "MICB";
  public static final String MAIB = "MAIB";
  public static final String FINCOMBANK = "FinComBank";

  public static final String BUY = "Купить";
  public static final String DASH = "-";

  public static final String USD = "USD";
  public static final String EUR = "EUR";
  public static final String RUB = "RUB";
  public static final String RON = "RON";
  public static final String UAH = "UAH";

  public static final String EXCHANGE_RATE_ONLY_IN_MAIN_OFFICE = "*Курсы действительны только в главном офисе банка и могут отличаться в его территориальных подразделениях.*";
}
