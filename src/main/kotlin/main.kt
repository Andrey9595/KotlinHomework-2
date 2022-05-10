enum class CardTypes {
    Mastercard, Maestro, Visa, Mir, VKPay
}

fun main() {
    val paymentSeq = sequenceOf(10_00L, 100_00L)

    val lastMonthPaymentSeq = sequenceOf(10_00L, 80_000_00L)

    for (monthPayment in lastMonthPaymentSeq) {
        for (payment in paymentSeq) {
            CardTypes.values().forEach {
                val commission = calcPaymentCommission(
                    cardType = it,
                    lastMonthPayments = monthPayment,
                    currentPayment = payment
                )
                println(
                    "Платежная система: $it, " +
                            "текущий перевод: ${payment / 100L} рублей ${payment % 100L} копеек, " +
                            "сумма переводов за месяц: ${monthPayment / 100L} рублей ${monthPayment % 100L} копеек"
                )
                println("Комиссия составит ${commission / 100L} рублей ${commission % 100L} копеек")
                println("")
            }
        }
    }
}

fun calcPaymentCommission(
    cardType: CardTypes = CardTypes.VKPay,
    lastMonthPayments: Long = 0, currentPayment: Long
): Long {
    val noCommission = 0L
    return when (cardType) {
        CardTypes.Mastercard, CardTypes.Maestro ->
            calcMastercardMaestroCommission(currentPayment, lastMonthPayments)
        CardTypes.Visa, CardTypes.Mir -> calcVisaMirCommission(currentPayment)
        else -> noCommission
    }
}

fun calcMastercardMaestroCommission(currentPayment: Long, lastMonthPayments: Long): Long {
    val noCommission = 0L
    val commissionPercent = 0.6F
    val commissionFix = 20_00.0F
    val mastercardMaestroMonthLimit = 75_000_00
    return if (lastMonthPayments < mastercardMaestroMonthLimit) {
        noCommission
    } else {
        (currentPayment * commissionPercent / 100.0F + commissionFix).toLong()
    }
}

fun calcVisaMirCommission(currentPayment: Long): Long {
    val commissionPercent = 0.75F
    val commissionMin = 35_00L
    val result = (currentPayment * commissionPercent / 100.0F).toLong()
    return if (result >= commissionMin) result else commissionMin
}