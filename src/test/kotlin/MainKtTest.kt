import org.junit.Test

import org.junit.Assert.*

class MainKtTest {

    @Test
    fun calcVisaMirCommission_commissionMin() {
        val currentPayment = 10_00L
        val result = calcVisaMirCommission(currentPayment)
        assertEquals(35_00L, result)
    }
    //стандартная коммиссия Виза/Мир
    @Test
    fun calcVisaMirCommission_commissionPercent() {
        val currentPayment = ((35_00L + 1L) * 100L / 0.75F).toLong()
        val result = calcVisaMirCommission(currentPayment)
        assertEquals((currentPayment * 0.75F / 100.0F).toLong(), result)
    }
    //месячный лимит не достигнут
    @Test
    fun calcMastercardMaestro_noCommission() {
        val currentPayment = 10_00L
        val monthLimit     = 10_000_00L
        val result = calcMastercardMaestroCommission(currentPayment, monthLimit)
        assertEquals(0L, result)
    }
    //месячный лимит превышен
    @Test
    fun calcMastercardMaestro_CommissionPercent() {
        val currentPayment = 1_000_00L  // 1000 * 0.6% = 6р + 20р фикс = 26р
        val monthLimit     = 80_000_00L //>75_000_00
        val result = calcMastercardMaestroCommission(currentPayment, monthLimit)
        assertEquals(26_00L, result)
    }
    //тест маестро
    @Test
    fun calcPaymentCommission_Maestro_noCommission() {
        val currentPayment = 10_00L
        val monthLimit     = 10_000_00L
        val cardType       = CardTypes.Maestro
        val result = calcPaymentCommission(
            cardType          = cardType,
            lastMonthPayments = monthLimit,
            currentPayment    = currentPayment
        )
        assertEquals(0L, result)
    }
    //тест мастеркард
    @Test
    fun calcPaymentCommission_Mastercard_noCommission() {
        val currentPayment = 10_00L
        val monthLimit     = 10_000_00L
        val cardType       = CardTypes.Mastercard
        val result = calcPaymentCommission(
            cardType          = cardType,
            lastMonthPayments = monthLimit,
            currentPayment    = currentPayment
        )
        assertEquals(0L, result)
    }

    //Минимальная коммиссия Виза
    @Test
    fun calcPaymentCommission_Visa_minCommission() {
        val currentPayment = 10_00L
        val monthLimit     = 10_000_00L
        val cardType       = CardTypes.Visa
        val result = calcPaymentCommission(
            cardType          = cardType,
            lastMonthPayments = monthLimit,
            currentPayment    = currentPayment
        )
        assertEquals(35_00L, result)
    }

    //Минимальная коммиссия Мир
    @Test
    fun calcPaymentCommission_Mir_minCommission() {
        val currentPayment = 10_00L
        val monthLimit     = 10_000_00L
        val cardType       = CardTypes.Mir
        val result = calcPaymentCommission(
            cardType          = cardType,
            lastMonthPayments = monthLimit,
            currentPayment    = currentPayment
        )
        assertEquals(35_00L, result)
    }

    //VKPay
    @Test
    fun calcPaymentCommission_VkPay_noCommission() {
        val currentPayment = 10_00L
        val monthLimit     = 10_000_00L
        val cardType       = CardTypes.VKPay
        val result = calcPaymentCommission(
            cardType          = cardType,
            lastMonthPayments = monthLimit,
            currentPayment    = currentPayment
        )
        assertEquals(0L, result)
    }

    //параметры по умолчанию
    @Test
    fun calcPaymentCommission_defaultParams() {
        val currentPayment = 10_00L
        val result = calcPaymentCommission(currentPayment = currentPayment)
        assertEquals(1L, result)
    }
}