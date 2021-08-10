//package com.example.smbeaconclient.firebase
//
//class zz {
//    public void SendNotifications(RemoteMessage message) { try { NotificationManager manager = (NotificationManager)GetSystemService(NotificationService);
//
//        var seed = Convert.ToInt32(Regex.Match(Guid.NewGuid().ToString(), @"\d+").Value);
//        int id = new Random(seed).Next(000000000, 999999999);
//
//        var push = new Intent();
//
//        var fullScreenPendingIntent = PendingIntent.GetActivity(this, 0,
//            push, PendingIntentFlags.CancelCurrent);
//
//
//        NotificationCompat.Builder notification;
//
//        if (Build.VERSION.SdkInt >= BuildVersionCodes.O)
//        {
//            var chan1 = new NotificationChannel(myUrgentChannel,
//            new Java.Lang.String("Primary"), NotificationImportance.High);
//            chan1.LightColor = Color.Green;
//
//            manager.CreateNotificationChannel(chan1);
//
//            notification = new NotificationCompat.Builder(this, myUrgentChannel).SetOngoing(true);
//        }
//        else
//        {
//            notification = new NotificationCompat.Builder(this);
//        }
//
//        notification.SetOngoing(true);
//        notification.SetAutoCancel(true);
//
//        var clickaction = message.GetNotification().ClickAction;
//        notification.SetContentIntent(fullScreenPendingIntent)
//            .SetContentTitle(message.GetNotification().Title)
//            .SetContentText(message.GetNotification().Body)
//            .SetLargeIcon(BitmapFactory.DecodeResource(Resources, Resource.Drawable.img123))
//            .SetSmallIcon(Resource.Drawable.img123)
//            .SetStyle((new NotificationCompat.BigTextStyle()))
//            .SetPriority(NotificationCompat.PriorityHigh)
//            .SetColor(0x9c6114)
//            .SetAutoCancel(true)
//            .SetOngoing(true);
//
//
//        manager.Notify(id, notification.Build());
//
//        // start a new app here
//        Intent i = PackageManager.GetLaunchIntentForPackage("com.companyname.formapp1");
//        StartActivity(i);
//
//    }
//    catch (System.Exception ex)
//        {
//            System.Diagnostics.Debug.WriteLine("ecsption = "+ ex.StackTrace);
//        }
//    }
//}