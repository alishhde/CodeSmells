 public synchronized void setNotificationRegistration(final String token) {
 // Get the NotificationRegistrationManager from the platform
 ConnectedDevicesNotificationRegistrationManager registrationManager = mPlatform.getNotificationRegistrationManager();


 // Create a NotificationRegistration obect to store all notification information
 ConnectedDevicesNotificationRegistration registration = new ConnectedDevicesNotificationRegistration();
 registration.setType(ConnectedDevicesNotificationType.FCM);
 registration.setToken(token);
 registration.setAppId(Secrets.FCM_SENDER_ID);
 registration.setAppDisplayName("GraphNotificationsSample");


 Log.i(TAG, "Completing the RomeNotificationReceiver operation with token: " + token);


 // For each prepared account, register for notifications
 for (final Account account : mAccounts) {
 registrationManager.registerForAccountAsync(account.getAccount(), registration)
                    .whenCompleteAsync((Boolean success, Throwable throwable) -> {
 if (throwable != null) {
 Log.e(TAG, "Exception encountered in registerForAccountAsync", throwable);
                        } else if (!success) {
 Log.e(TAG, "Failed to register account " + account.getAccount().getId() + " for cloud notifications!");
                        } else {
 Log.i(TAG, "Successfully registered account " + account.getAccount().getId() + " for cloud notifications");
                        }
                    });
        }


 // The two cases of receiving a new notification token are:
 // 1. A notification registration is asked for and now it is available. In this case there is a pending promise that was made
 //    at the time of requesting the information. It now needs to be completed.
 // 2. The account is already registered but for whatever reason the registration changes (GCM/FCM gives the app a new token)
 //
 // In order to most cleanly handle both cases set the new notification information and then trigger a re registration of all accounts
 // that are in good standing.
 RomeNotificationReceiver.setNotificationRegistration(registration);


 // For all the accounts which have been prepared successfully, perform SDK registration
 for (Account account : mAccounts) {
 if (account.getRegistrationState() == AccountRegistrationState.IN_APP_CACHE_AND_SDK_CACHE) {
 account.registerAccountWithSdkAsync();
            }
        }
    }