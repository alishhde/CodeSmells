 public void validateDepositDetailForUpdate(final JsonElement element, final FromJsonHelper fromApiJsonHelper,
 final DataValidatorBuilder baseDataValidator) {
 if (fromApiJsonHelper.parameterExists(nameParamName, element)) {
 final String name = fromApiJsonHelper.extractStringNamed(nameParamName, element);
 baseDataValidator.reset().parameter(nameParamName).value(name).notBlank().notExceedingLengthOf(100);
        }


 if (fromApiJsonHelper.parameterExists(shortNameParamName, element)) {
 final String shortName = fromApiJsonHelper.extractStringNamed(shortNameParamName, element);
 baseDataValidator.reset().parameter(shortNameParamName).value(shortName).notBlank().notExceedingLengthOf(4);
        }


 if (fromApiJsonHelper.parameterExists(descriptionParamName, element)) {
 final String description = fromApiJsonHelper.extractStringNamed(descriptionParamName, element);
 baseDataValidator.reset().parameter(descriptionParamName).value(description).notBlank().notExceedingLengthOf(500);
        }


 if (fromApiJsonHelper.parameterExists(currencyCodeParamName, element)) {
 final String currencyCode = fromApiJsonHelper.extractStringNamed(currencyCodeParamName, element);
 baseDataValidator.reset().parameter(currencyCodeParamName).value(currencyCode).notBlank();
        }


 if (fromApiJsonHelper.parameterExists(digitsAfterDecimalParamName, element)) {
 final Integer digitsAfterDecimal = fromApiJsonHelper.extractIntegerSansLocaleNamed(digitsAfterDecimalParamName, element);
 baseDataValidator.reset().parameter(digitsAfterDecimalParamName).value(digitsAfterDecimal).notNull().inMinMaxRange(0, 6);
        }


 if (fromApiJsonHelper.parameterExists(inMultiplesOfParamName, element)) {
 final Integer inMultiplesOf = fromApiJsonHelper.extractIntegerNamed(inMultiplesOfParamName, element, Locale.getDefault());
 baseDataValidator.reset().parameter(inMultiplesOfParamName).value(inMultiplesOf).ignoreIfNull().integerZeroOrGreater();
        }


 if (fromApiJsonHelper.parameterExists(nominalAnnualInterestRateParamName, element)) {
 final BigDecimal interestRate = fromApiJsonHelper.extractBigDecimalWithLocaleNamed(nominalAnnualInterestRateParamName, element);
 baseDataValidator.reset().parameter(nominalAnnualInterestRateParamName).value(interestRate).notNull().zeroOrPositiveAmount();
        }


 if (fromApiJsonHelper.parameterExists(interestCompoundingPeriodTypeParamName, element)) {
 final Integer interestCompoundingPeriodType = fromApiJsonHelper.extractIntegerSansLocaleNamed(
 interestCompoundingPeriodTypeParamName, element);
 baseDataValidator.reset().parameter(interestCompoundingPeriodTypeParamName).value(interestCompoundingPeriodType).notNull()
                    .isOneOfTheseValues(SavingsCompoundingInterestPeriodType.integerValues());
        }


 if (fromApiJsonHelper.parameterExists(interestCalculationTypeParamName, element)) {
 final Integer interestCalculationType = fromApiJsonHelper.extractIntegerSansLocaleNamed(interestCalculationTypeParamName,
 element);
 baseDataValidator.reset().parameter(interestCalculationTypeParamName).value(interestCalculationType).notNull()
                    .inMinMaxRange(1, 2);
        }


 if (fromApiJsonHelper.parameterExists(interestCalculationDaysInYearTypeParamName, element)) {
 final Integer interestCalculationDaysInYearType = fromApiJsonHelper.extractIntegerSansLocaleNamed(
 interestCalculationDaysInYearTypeParamName, element);
 baseDataValidator.reset().parameter(interestCalculationDaysInYearTypeParamName).value(interestCalculationDaysInYearType)
                    .notNull().isOneOfTheseValues(360, 365);
        }


 if (fromApiJsonHelper.parameterExists(minRequiredOpeningBalanceParamName, element)) {
 final BigDecimal minOpeningBalance = fromApiJsonHelper.extractBigDecimalWithLocaleNamed(minRequiredOpeningBalanceParamName,
 element);
 baseDataValidator.reset().parameter(minRequiredOpeningBalanceParamName).value(minOpeningBalance).ignoreIfNull()
                    .zeroOrPositiveAmount();
        }


 if (fromApiJsonHelper.parameterExists(lockinPeriodFrequencyParamName, element)) {
 final Integer lockinPeriodFrequency = fromApiJsonHelper.extractIntegerWithLocaleNamed(lockinPeriodFrequencyParamName, element);
 baseDataValidator.reset().parameter(lockinPeriodFrequencyParamName).value(lockinPeriodFrequency).ignoreIfNull()
                    .integerZeroOrGreater();
        }


 if (fromApiJsonHelper.parameterExists(lockinPeriodFrequencyTypeParamName, element)) {
 final Integer lockinPeriodFrequencyType = fromApiJsonHelper.extractIntegerSansLocaleNamed(lockinPeriodFrequencyTypeParamName,
 element);
 baseDataValidator.reset().parameter(lockinPeriodFrequencyTypeParamName).value(lockinPeriodFrequencyType).inMinMaxRange(0, 3);
        }


 if (fromApiJsonHelper.parameterExists(withdrawalFeeForTransfersParamName, element)) {
 final Boolean isWithdrawalFeeApplicableForTransfers = fromApiJsonHelper.extractBooleanNamed(withdrawalFeeForTransfersParamName,
 element);
 baseDataValidator.reset().parameter(withdrawalFeeForTransfersParamName).value(isWithdrawalFeeApplicableForTransfers)
                    .ignoreIfNull().validateForBooleanValue();
        }


 if (fromApiJsonHelper.parameterExists(feeAmountParamName, element)) {
 final BigDecimal annualFeeAmount = fromApiJsonHelper.extractBigDecimalWithLocaleNamed(feeAmountParamName, element);
 baseDataValidator.reset().parameter(feeAmountParamName).value(annualFeeAmount).ignoreIfNull().zeroOrPositiveAmount();
        }


 if (fromApiJsonHelper.parameterExists(feeOnMonthDayParamName, element)) {
 final MonthDay monthDayOfAnnualFee = fromApiJsonHelper.extractMonthDayNamed(feeOnMonthDayParamName, element);
 baseDataValidator.reset().parameter(feeOnMonthDayParamName).value(monthDayOfAnnualFee).ignoreIfNull();
        }


 if (this.fromApiJsonHelper.parameterExists(minBalanceForInterestCalculationParamName, element)) {
 final BigDecimal minBalanceForInterestCalculation = this.fromApiJsonHelper.extractBigDecimalWithLocaleNamed(
 minBalanceForInterestCalculationParamName, element);
 baseDataValidator.reset().parameter(minBalanceForInterestCalculationParamName).value(minBalanceForInterestCalculation)
                    .ignoreIfNull().zeroOrPositiveAmount();
        }


 final Long savingsControlAccountId = fromApiJsonHelper.extractLongNamed(
 SAVINGS_PRODUCT_ACCOUNTING_PARAMS.SAVINGS_CONTROL.getValue(), element);
 baseDataValidator.reset().parameter(SAVINGS_PRODUCT_ACCOUNTING_PARAMS.SAVINGS_CONTROL.getValue()).value(savingsControlAccountId)
                .ignoreIfNull().integerGreaterThanZero();


 final Long savingsReferenceAccountId = fromApiJsonHelper.extractLongNamed(
 SAVINGS_PRODUCT_ACCOUNTING_PARAMS.SAVINGS_REFERENCE.getValue(), element);
 baseDataValidator.reset().parameter(SAVINGS_PRODUCT_ACCOUNTING_PARAMS.SAVINGS_REFERENCE.getValue())
                .value(savingsReferenceAccountId).ignoreIfNull().integerGreaterThanZero();


 final Long transfersInSuspenseAccountId = fromApiJsonHelper.extractLongNamed(
 SAVINGS_PRODUCT_ACCOUNTING_PARAMS.TRANSFERS_SUSPENSE.getValue(), element);
 baseDataValidator.reset().parameter(SAVINGS_PRODUCT_ACCOUNTING_PARAMS.TRANSFERS_SUSPENSE.getValue())
                .value(transfersInSuspenseAccountId).ignoreIfNull().integerGreaterThanZero();


 final Long interestOnSavingsAccountId = fromApiJsonHelper.extractLongNamed(
 SAVINGS_PRODUCT_ACCOUNTING_PARAMS.INTEREST_ON_SAVINGS.getValue(), element);
 baseDataValidator.reset().parameter(SAVINGS_PRODUCT_ACCOUNTING_PARAMS.INTEREST_ON_SAVINGS.getValue())
                .value(interestOnSavingsAccountId).ignoreIfNull().integerGreaterThanZero();


 final Long incomeFromFeeId = fromApiJsonHelper.extractLongNamed(SAVINGS_PRODUCT_ACCOUNTING_PARAMS.INCOME_FROM_FEES.getValue(),
 element);
 baseDataValidator.reset().parameter(SAVINGS_PRODUCT_ACCOUNTING_PARAMS.INCOME_FROM_FEES.getValue()).value(incomeFromFeeId)
                .ignoreIfNull().integerGreaterThanZero();


 final Long incomeFromPenaltyId = fromApiJsonHelper.extractLongNamed(
 SAVINGS_PRODUCT_ACCOUNTING_PARAMS.INCOME_FROM_PENALTIES.getValue(), element);
 baseDataValidator.reset().parameter(SAVINGS_PRODUCT_ACCOUNTING_PARAMS.INCOME_FROM_PENALTIES.getValue()).value(incomeFromPenaltyId)
                .ignoreIfNull().integerGreaterThanZero();


 validatePaymentChannelFundSourceMappings(fromApiJsonHelper, baseDataValidator, element);
 validateChargeToIncomeAccountMappings(fromApiJsonHelper, baseDataValidator, element);
 validateTaxWithHoldingParams(baseDataValidator, element, false);
    }