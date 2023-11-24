package util

import com.kinandcarta.transactionsapi.util.DateUtils
import spock.lang.Specification

import java.time.LocalDate

import static java.time.temporal.ChronoField.EPOCH_DAY

class DateUtilsSpec extends Specification {

    def "should convert timestamp into date"() {
        given: "a timestamp and a format"
        def timestamp = LocalDate.of(2023, 12, 10).getLong(EPOCH_DAY);

        expect: "to return the correct date in the specified format"
        def actual = DateUtils.formatEpochDateAsString(timestamp, format)

        actual == expected

        where:
        format       | expected
        'yyyy-MM-dd' | "2023-12-10"
        'MM-dd-yyyy' | "12-10-2023"
    }
}