 public static class APIRequestGet extends APIRequest<AdCampaignActivity> {


 AdCampaignActivity lastResponse = null;
 @Override
 public AdCampaignActivity getLastResponse() {
 return lastResponse;
    }
 public static final String[] PARAMS = {
    };


 public static final String[] FIELDS = {
 "auto_create_lookalike_new",
 "auto_create_lookalike_old",
 "bid_adjustments_spec_new",
 "bid_adjustments_spec_old",
 "bid_amount_new",
 "bid_amount_old",
 "bid_constraints_new",
 "bid_constraints_old",
 "bid_info_new",
 "bid_info_old",
 "bid_strategy_new",
 "bid_strategy_old",
 "bid_type_new",
 "bid_type_old",
 "billing_event_new",
 "billing_event_old",
 "brande_audience_id_new",
 "brande_audience_id_old",
 "budget_limit_new",
 "budget_limit_old",
 "created_time",
 "daily_impressions_new",
 "daily_impressions_old",
 "dco_mode_new",
 "dco_mode_old",
 "delivery_behavior_new",
 "delivery_behavior_old",
 "destination_type_new",
 "destination_type_old",
 "event_time",
 "event_type",
 "id",
 "invoicing_limit_new",
 "invoicing_limit_old",
 "min_spend_target_new",
 "min_spend_target_old",
 "name_new",
 "name_old",
 "optimization_goal_new",
 "optimization_goal_old",
 "pacing_type_new",
 "pacing_type_old",
 "run_status_new",
 "run_status_old",
 "schedule_new",
 "schedule_old",
 "spend_cap_new",
 "spend_cap_old",
 "start_time_new",
 "start_time_old",
 "stop_time_new",
 "stop_time_old",
 "targeting_expansion_new",
 "targeting_expansion_old",
 "updated_time_new",
 "updated_time_old",
    };


 @Override
 public AdCampaignActivity parseResponse(String response, String header) throws APIException {
 return AdCampaignActivity.parseResponse(response, getContext(), this, header).head();
    }


 @Override
 public AdCampaignActivity execute() throws APIException {
 return execute(new HashMap<String, Object>());
    }


 @Override
 public AdCampaignActivity execute(Map<String, Object> extraParams) throws APIException {
 ResponseWrapper rw = executeInternal(extraParams);
 lastResponse = parseResponse(rw.getBody(), rw.getHeader());
 return lastResponse;
    }


 public ListenableFuture<AdCampaignActivity> executeAsync() throws APIException {
 return executeAsync(new HashMap<String, Object>());
    };


 public ListenableFuture<AdCampaignActivity> executeAsync(Map<String, Object> extraParams) throws APIException {
 return Futures.transform(
 executeAsyncInternal(extraParams),
 new Function<ResponseWrapper, AdCampaignActivity>() {
 public AdCampaignActivity apply(ResponseWrapper result) {
 try {
 return APIRequestGet.this.parseResponse(result.getBody(), result.getHeader());
             } catch (Exception e) {
 throw new RuntimeException(e);
             }
           }
         }
      );
    };


 public APIRequestGet(String nodeId, APIContext context) {
 super(context, nodeId, "/", "GET", Arrays.asList(PARAMS));
    }


 @Override
 public APIRequestGet setParam(String param, Object value) {
 setParamInternal(param, value);
 return this;
    }


 @Override
 public APIRequestGet setParams(Map<String, Object> params) {
 setParamsInternal(params);
 return this;
    }




 public APIRequestGet requestAllFields () {
 return this.requestAllFields(true);
    }


 public APIRequestGet requestAllFields (boolean value) {
 for (String field : FIELDS) {
 this.requestField(field, value);
      }
 return this;
    }


 @Override
 public APIRequestGet requestFields (List<String> fields) {
 return this.requestFields(fields, true);
    }


 @Override
 public APIRequestGet requestFields (List<String> fields, boolean value) {
 for (String field : fields) {
 this.requestField(field, value);
      }
 return this;
    }


 @Override
 public APIRequestGet requestField (String field) {
 this.requestField(field, true);
 return this;
    }


 @Override
 public APIRequestGet requestField (String field, boolean value) {
 this.requestFieldInternal(field, value);
 return this;
    }


 public APIRequestGet requestAutoCreateLookalikeNewField () {
 return this.requestAutoCreateLookalikeNewField(true);
    }
 public APIRequestGet requestAutoCreateLookalikeNewField (boolean value) {
 this.requestField("auto_create_lookalike_new", value);
 return this;
    }
 public APIRequestGet requestAutoCreateLookalikeOldField () {
 return this.requestAutoCreateLookalikeOldField(true);
    }
 public APIRequestGet requestAutoCreateLookalikeOldField (boolean value) {
 this.requestField("auto_create_lookalike_old", value);
 return this;
    }
 public APIRequestGet requestBidAdjustmentsSpecNewField () {
 return this.requestBidAdjustmentsSpecNewField(true);
    }
 public APIRequestGet requestBidAdjustmentsSpecNewField (boolean value) {
 this.requestField("bid_adjustments_spec_new", value);
 return this;
    }
 public APIRequestGet requestBidAdjustmentsSpecOldField () {
 return this.requestBidAdjustmentsSpecOldField(true);
    }
 public APIRequestGet requestBidAdjustmentsSpecOldField (boolean value) {
 this.requestField("bid_adjustments_spec_old", value);
 return this;
    }
 public APIRequestGet requestBidAmountNewField () {
 return this.requestBidAmountNewField(true);
    }
 public APIRequestGet requestBidAmountNewField (boolean value) {
 this.requestField("bid_amount_new", value);
 return this;
    }
 public APIRequestGet requestBidAmountOldField () {
 return this.requestBidAmountOldField(true);
    }
 public APIRequestGet requestBidAmountOldField (boolean value) {
 this.requestField("bid_amount_old", value);
 return this;
    }
 public APIRequestGet requestBidConstraintsNewField () {
 return this.requestBidConstraintsNewField(true);
    }
 public APIRequestGet requestBidConstraintsNewField (boolean value) {
 this.requestField("bid_constraints_new", value);
 return this;
    }
 public APIRequestGet requestBidConstraintsOldField () {
 return this.requestBidConstraintsOldField(true);
    }
 public APIRequestGet requestBidConstraintsOldField (boolean value) {
 this.requestField("bid_constraints_old", value);
 return this;
    }
 public APIRequestGet requestBidInfoNewField () {
 return this.requestBidInfoNewField(true);
    }
 public APIRequestGet requestBidInfoNewField (boolean value) {
 this.requestField("bid_info_new", value);
 return this;
    }
 public APIRequestGet requestBidInfoOldField () {
 return this.requestBidInfoOldField(true);
    }
 public APIRequestGet requestBidInfoOldField (boolean value) {
 this.requestField("bid_info_old", value);
 return this;
    }
 public APIRequestGet requestBidStrategyNewField () {
 return this.requestBidStrategyNewField(true);
    }
 public APIRequestGet requestBidStrategyNewField (boolean value) {
 this.requestField("bid_strategy_new", value);
 return this;
    }
 public APIRequestGet requestBidStrategyOldField () {
 return this.requestBidStrategyOldField(true);
    }
 public APIRequestGet requestBidStrategyOldField (boolean value) {
 this.requestField("bid_strategy_old", value);
 return this;
    }
 public APIRequestGet requestBidTypeNewField () {
 return this.requestBidTypeNewField(true);
    }
 public APIRequestGet requestBidTypeNewField (boolean value) {
 this.requestField("bid_type_new", value);
 return this;
    }
 public APIRequestGet requestBidTypeOldField () {
 return this.requestBidTypeOldField(true);
    }
 public APIRequestGet requestBidTypeOldField (boolean value) {
 this.requestField("bid_type_old", value);
 return this;
    }
 public APIRequestGet requestBillingEventNewField () {
 return this.requestBillingEventNewField(true);
    }
 public APIRequestGet requestBillingEventNewField (boolean value) {
 this.requestField("billing_event_new", value);
 return this;
    }
 public APIRequestGet requestBillingEventOldField () {
 return this.requestBillingEventOldField(true);
    }
 public APIRequestGet requestBillingEventOldField (boolean value) {
 this.requestField("billing_event_old", value);
 return this;
    }
 public APIRequestGet requestBrandeAudienceIdNewField () {
 return this.requestBrandeAudienceIdNewField(true);
    }
 public APIRequestGet requestBrandeAudienceIdNewField (boolean value) {
 this.requestField("brande_audience_id_new", value);
 return this;
    }
 public APIRequestGet requestBrandeAudienceIdOldField () {
 return this.requestBrandeAudienceIdOldField(true);
    }
 public APIRequestGet requestBrandeAudienceIdOldField (boolean value) {
 this.requestField("brande_audience_id_old", value);
 return this;
    }
 public APIRequestGet requestBudgetLimitNewField () {
 return this.requestBudgetLimitNewField(true);
    }
 public APIRequestGet requestBudgetLimitNewField (boolean value) {
 this.requestField("budget_limit_new", value);
 return this;
    }
 public APIRequestGet requestBudgetLimitOldField () {
 return this.requestBudgetLimitOldField(true);
    }
 public APIRequestGet requestBudgetLimitOldField (boolean value) {
 this.requestField("budget_limit_old", value);
 return this;
    }
 public APIRequestGet requestCreatedTimeField () {
 return this.requestCreatedTimeField(true);
    }
 public APIRequestGet requestCreatedTimeField (boolean value) {
 this.requestField("created_time", value);
 return this;
    }
 public APIRequestGet requestDailyImpressionsNewField () {
 return this.requestDailyImpressionsNewField(true);
    }
 public APIRequestGet requestDailyImpressionsNewField (boolean value) {
 this.requestField("daily_impressions_new", value);
 return this;
    }
 public APIRequestGet requestDailyImpressionsOldField () {
 return this.requestDailyImpressionsOldField(true);
    }
 public APIRequestGet requestDailyImpressionsOldField (boolean value) {
 this.requestField("daily_impressions_old", value);
 return this;
    }
 public APIRequestGet requestDcoModeNewField () {
 return this.requestDcoModeNewField(true);
    }
 public APIRequestGet requestDcoModeNewField (boolean value) {
 this.requestField("dco_mode_new", value);
 return this;
    }
 public APIRequestGet requestDcoModeOldField () {
 return this.requestDcoModeOldField(true);
    }
 public APIRequestGet requestDcoModeOldField (boolean value) {
 this.requestField("dco_mode_old", value);
 return this;
    }
 public APIRequestGet requestDeliveryBehaviorNewField () {
 return this.requestDeliveryBehaviorNewField(true);
    }
 public APIRequestGet requestDeliveryBehaviorNewField (boolean value) {
 this.requestField("delivery_behavior_new", value);
 return this;
    }
 public APIRequestGet requestDeliveryBehaviorOldField () {
 return this.requestDeliveryBehaviorOldField(true);
    }
 public APIRequestGet requestDeliveryBehaviorOldField (boolean value) {
 this.requestField("delivery_behavior_old", value);
 return this;
    }
 public APIRequestGet requestDestinationTypeNewField () {
 return this.requestDestinationTypeNewField(true);
    }
 public APIRequestGet requestDestinationTypeNewField (boolean value) {
 this.requestField("destination_type_new", value);
 return this;
    }
 public APIRequestGet requestDestinationTypeOldField () {
 return this.requestDestinationTypeOldField(true);
    }
 public APIRequestGet requestDestinationTypeOldField (boolean value) {
 this.requestField("destination_type_old", value);
 return this;
    }
 public APIRequestGet requestEventTimeField () {
 return this.requestEventTimeField(true);
    }
 public APIRequestGet requestEventTimeField (boolean value) {
 this.requestField("event_time", value);
 return this;
    }
 public APIRequestGet requestEventTypeField () {
 return this.requestEventTypeField(true);
    }
 public APIRequestGet requestEventTypeField (boolean value) {
 this.requestField("event_type", value);
 return this;
    }
 public APIRequestGet requestIdField () {
 return this.requestIdField(true);
    }
 public APIRequestGet requestIdField (boolean value) {
 this.requestField("id", value);
 return this;
    }
 public APIRequestGet requestInvoicingLimitNewField () {
 return this.requestInvoicingLimitNewField(true);
    }
 public APIRequestGet requestInvoicingLimitNewField (boolean value) {
 this.requestField("invoicing_limit_new", value);
 return this;
    }
 public APIRequestGet requestInvoicingLimitOldField () {
 return this.requestInvoicingLimitOldField(true);
    }
 public APIRequestGet requestInvoicingLimitOldField (boolean value) {
 this.requestField("invoicing_limit_old", value);
 return this;
    }
 public APIRequestGet requestMinSpendTargetNewField () {
 return this.requestMinSpendTargetNewField(true);
    }
 public APIRequestGet requestMinSpendTargetNewField (boolean value) {
 this.requestField("min_spend_target_new", value);
 return this;
    }
 public APIRequestGet requestMinSpendTargetOldField () {
 return this.requestMinSpendTargetOldField(true);
    }
 public APIRequestGet requestMinSpendTargetOldField (boolean value) {
 this.requestField("min_spend_target_old", value);
 return this;
    }
 public APIRequestGet requestNameNewField () {
 return this.requestNameNewField(true);
    }
 public APIRequestGet requestNameNewField (boolean value) {
 this.requestField("name_new", value);
 return this;
    }
 public APIRequestGet requestNameOldField () {
 return this.requestNameOldField(true);
    }
 public APIRequestGet requestNameOldField (boolean value) {
 this.requestField("name_old", value);
 return this;
    }
 public APIRequestGet requestOptimizationGoalNewField () {
 return this.requestOptimizationGoalNewField(true);
    }
 public APIRequestGet requestOptimizationGoalNewField (boolean value) {
 this.requestField("optimization_goal_new", value);
 return this;
    }
 public APIRequestGet requestOptimizationGoalOldField () {
 return this.requestOptimizationGoalOldField(true);
    }
 public APIRequestGet requestOptimizationGoalOldField (boolean value) {
 this.requestField("optimization_goal_old", value);
 return this;
    }
 public APIRequestGet requestPacingTypeNewField () {
 return this.requestPacingTypeNewField(true);
    }
 public APIRequestGet requestPacingTypeNewField (boolean value) {
 this.requestField("pacing_type_new", value);
 return this;
    }
 public APIRequestGet requestPacingTypeOldField () {
 return this.requestPacingTypeOldField(true);
    }
 public APIRequestGet requestPacingTypeOldField (boolean value) {
 this.requestField("pacing_type_old", value);
 return this;
    }
 public APIRequestGet requestRunStatusNewField () {
 return this.requestRunStatusNewField(true);
    }
 public APIRequestGet requestRunStatusNewField (boolean value) {
 this.requestField("run_status_new", value);
 return this;
    }
 public APIRequestGet requestRunStatusOldField () {
 return this.requestRunStatusOldField(true);
    }
 public APIRequestGet requestRunStatusOldField (boolean value) {
 this.requestField("run_status_old", value);
 return this;
    }
 public APIRequestGet requestScheduleNewField () {
 return this.requestScheduleNewField(true);
    }
 public APIRequestGet requestScheduleNewField (boolean value) {
 this.requestField("schedule_new", value);
 return this;
    }
 public APIRequestGet requestScheduleOldField () {
 return this.requestScheduleOldField(true);
    }
 public APIRequestGet requestScheduleOldField (boolean value) {
 this.requestField("schedule_old", value);
 return this;
    }
 public APIRequestGet requestSpendCapNewField () {
 return this.requestSpendCapNewField(true);
    }
 public APIRequestGet requestSpendCapNewField (boolean value) {
 this.requestField("spend_cap_new", value);
 return this;
    }
 public APIRequestGet requestSpendCapOldField () {
 return this.requestSpendCapOldField(true);
    }
 public APIRequestGet requestSpendCapOldField (boolean value) {
 this.requestField("spend_cap_old", value);
 return this;
    }
 public APIRequestGet requestStartTimeNewField () {
 return this.requestStartTimeNewField(true);
    }
 public APIRequestGet requestStartTimeNewField (boolean value) {
 this.requestField("start_time_new", value);
 return this;
    }
 public APIRequestGet requestStartTimeOldField () {
 return this.requestStartTimeOldField(true);
    }
 public APIRequestGet requestStartTimeOldField (boolean value) {
 this.requestField("start_time_old", value);
 return this;
    }
 public APIRequestGet requestStopTimeNewField () {
 return this.requestStopTimeNewField(true);
    }
 public APIRequestGet requestStopTimeNewField (boolean value) {
 this.requestField("stop_time_new", value);
 return this;
    }
 public APIRequestGet requestStopTimeOldField () {
 return this.requestStopTimeOldField(true);
    }
 public APIRequestGet requestStopTimeOldField (boolean value) {
 this.requestField("stop_time_old", value);
 return this;
    }
 public APIRequestGet requestTargetingExpansionNewField () {
 return this.requestTargetingExpansionNewField(true);
    }
 public APIRequestGet requestTargetingExpansionNewField (boolean value) {
 this.requestField("targeting_expansion_new", value);
 return this;
    }
 public APIRequestGet requestTargetingExpansionOldField () {
 return this.requestTargetingExpansionOldField(true);
    }
 public APIRequestGet requestTargetingExpansionOldField (boolean value) {
 this.requestField("targeting_expansion_old", value);
 return this;
    }
 public APIRequestGet requestUpdatedTimeNewField () {
 return this.requestUpdatedTimeNewField(true);
    }
 public APIRequestGet requestUpdatedTimeNewField (boolean value) {
 this.requestField("updated_time_new", value);
 return this;
    }
 public APIRequestGet requestUpdatedTimeOldField () {
 return this.requestUpdatedTimeOldField(true);
    }
 public APIRequestGet requestUpdatedTimeOldField (boolean value) {
 this.requestField("updated_time_old", value);
 return this;
    }
  }