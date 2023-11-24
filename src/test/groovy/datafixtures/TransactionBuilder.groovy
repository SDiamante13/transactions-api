package datafixtures

import com.kinandcarta.transactionsapi.domain.entity.Account
import com.kinandcarta.transactionsapi.domain.entity.Transaction

import static datafixtures.RandomGenerator.*

class TransactionBuilder {
    private long transactionId = randomLong()
    private long date = randomLong()
    private double amount = randomBigDecimal()
    private String merchantName = randomString()
    private String summary = randomString()
    private Account account = new AccountBuilder().build()


    def transactionId(long val) {
        transactionId = val;
        return this;
    }

    def date(long val) {
        date = val;
        return this;
    }

    def amount(double val) {
        amount = val;
        return this;
    }

    def merchantName(String val) {
        merchantName = val;
        return this;
    }

    def summary(String val) {
        summary = val;
        return this;
    }

    def account(Account val) {
        account = val;
        return this;
    }

    def build() {
        new Transaction(
                transactionId: randomLong(),
                date: randomLong(),
                amount: randomBigDecimal(),
                merchantName: randomString(),
                summary: randomString()

        )
    }
}