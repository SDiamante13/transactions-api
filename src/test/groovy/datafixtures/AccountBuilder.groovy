package datafixtures

import com.kinandcarta.transactionsapi.domain.entity.Account
import com.kinandcarta.transactionsapi.domain.entity.Transaction

class AccountBuilder {
    private long accountId = RandomGenerator.randomLong()
    private String memberName = RandomGenerator.randomString()
    private Set<Transaction> transactions;


    def accountId(long val) {
        accountId = val
        return this
    }

    def memberName(String val) {
        memberName = valxz
        return this
    }

    def transactions(Set<Transaction> val) {
        transactions = val
        return this
    }

    Account build() {
        return new Account(
                accountId: accountId,
                memberName: memberName,
                transactions: transactions
        )
    }
}
