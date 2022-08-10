 public void reloadExpectedTimeAndConfig(SLACalcStatus slaCalc) throws JPAExecutorException {
 SLARegistrationBean regBean = SLARegistrationQueryExecutor.getInstance().get(
 SLARegQuery.GET_SLA_EXPECTED_VALUE_CONFIG, slaCalc.getId());


 if (regBean.getExpectedDuration() > 0) {
 slaCalc.getSLARegistrationBean().setExpectedDuration(regBean.getExpectedDuration());
        }
 if (regBean.getExpectedEnd() != null) {
 slaCalc.getSLARegistrationBean().setExpectedEnd(regBean.getExpectedEnd());
        }
 if (regBean.getExpectedStart() != null) {
 slaCalc.getSLARegistrationBean().setExpectedStart(regBean.getExpectedStart());
        }
 if (regBean.getSLAConfigMap().containsKey(OozieClient.SLA_DISABLE_ALERT)) {
 slaCalc.getSLARegistrationBean().addToSLAConfigMap(OozieClient.SLA_DISABLE_ALERT,
 regBean.getSLAConfigMap().get(OozieClient.SLA_DISABLE_ALERT));
        }
 if (regBean.getNominalTime() != null) {
 slaCalc.getSLARegistrationBean().setNominalTime(regBean.getNominalTime());
        }
    }