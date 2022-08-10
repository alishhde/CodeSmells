 public CreateBudgetDetails build() {
 CreateBudgetDetails __instance__ =
 new CreateBudgetDetails(
 compartmentId,
 targetCompartmentId,
 displayName,
 description,
 amount,
 resetPeriod,
 freeformTags,
 definedTags);
 __instance__.__explicitlySet__.addAll(__explicitlySet__);
 return __instance__;
        }