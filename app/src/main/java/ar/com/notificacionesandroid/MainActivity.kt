package ar.com.notificacionesandroid

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.graphics.drawable.IconCompat
import ar.com.notificacionesandroid.Constant.INTENT_REQUEST
import ar.com.notificacionesandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val channelID = "channelId"
    private val channelName = "channelName"

    private lateinit var notificationOne: Notification
    private val notificationOneId = 0

    private lateinit var notificationTwo: Notification
    private val notificationTwoId = 1

    private lateinit var notificationThree: Notification
    private val notificationThreeId = 2

    private lateinit var notificationFour: Notification
    private val notificationFourId = 3

    private lateinit var notificationFive: Notification
    private val notificationFiveId = 4

    private lateinit var customNotification: Notification
    private val customNotificationId = 5

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        buildNotificationOne()
        buildNotificationTwo()
        buildNotificationThree()
        buildNotificationFour()
        buildNotificationFive()
        buildNotificationSix()

        buttonsListeners()

    }

    private fun buildNotificationSix() {
        val notificationLayout = RemoteViews(packageName, R.layout.notification_small)
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_expanded)

        customNotification = NotificationCompat.Builder(this, channelID).also {
            it.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
            it.setStyle(NotificationCompat.DecoratedCustomViewStyle())
            it.setCustomContentView(notificationLayout)
            it.setCustomBigContentView(notificationLayoutExpanded)
        }.build()
    }

    private fun buildNotificationFive() {
        val myBitmap = R.drawable.music.createBitmap(this)

        val intent = Intent()
        val pendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(INTENT_REQUEST, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        notificationFive = NotificationCompat.Builder(this, channelID).also {
            it.setSmallIcon(R.drawable.ic_baseline_library_music_24)
            it.setContentTitle("Multimedia Notification")
            it.setContentText("New Song")
            it.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            it.addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", pendingIntent)
            it.addAction(R.drawable.ic_baseline_pause_24, "Pause", pendingIntent)
            it.addAction(R.drawable.ic_baseline_skip_next_24, "Next", pendingIntent)
            it.setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView()
            )
            it.setLargeIcon(myBitmap)
        }.build()

    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun buildNotificationFour() {
        val mariaIcon = R.drawable.person.createBitmap(this)
        val ferIcon = R.drawable.person2.createBitmap(this)

        val message1 = NotificationCompat.MessagingStyle.Message(
            "I'm a Web Developer! ",
            System.currentTimeMillis(),
            androidx.core.app.Person.Builder().also {
                it.setName("Daiana Soto")
                it.setIcon(IconCompat.createWithAdaptiveBitmap(mariaIcon))
            }.build()
        )

        val message2 = NotificationCompat.MessagingStyle.Message(
            "I am an Android Developer!",
            System.currentTimeMillis(),
            androidx.core.app.Person.Builder().also {
                it.setName("Fernando Moreno")
                it.setIcon(IconCompat.createWithAdaptiveBitmap(ferIcon))
            }.build()
        )

        notificationFour = NotificationCompat.Builder(this, channelID).also {
            it.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
            it.setStyle(
                NotificationCompat.MessagingStyle(
                    androidx.core.app.Person.Builder().also { person ->
                        person.setName("My Name")
                    }.build()
                )
                    .addMessage(message1)
                    .addMessage(message2)
            )
        }.build()
    }

    private fun buildNotificationThree() {
        notificationThree = NotificationCompat.Builder(this, channelID).also {
            it.setSmallIcon(R.drawable.ic_baseline_email_24)
            it.setContentTitle("morenofernando@gmail.com")
            it.setContentText("You have 3 unread emails")
            it.setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("See free Android Source Codes from GitHub")
                    .addLine("You have won 1000 dollars!!")
                    .addLine("You item you have bought was sent.")
            )
        }.build()
    }

    private fun buildNotificationTwo() {
        val myBitmap = R.drawable.toronto.createBitmap(this)

        notificationTwo = NotificationCompat.Builder(this, channelID).also {
            it.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
            it.setContentTitle("Notification with Text")
            it.setContentText("Body's Notification")
            it.setLargeIcon(myBitmap)
            it.setStyle(
                NotificationCompat.BigTextStyle().bigText(getString(R.string.large_text))
            )
        }.build()
    }

    private fun buildNotificationOne() {
        val myBitmap = R.drawable.toronto.createBitmap(this)

        notificationOne = NotificationCompat.Builder(this, channelID).also {
            it.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
            it.setContentTitle("Notification with Image")
            it.setContentText("Body's Notification")
            it.setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(myBitmap)
                    .bigLargeIcon(null)
            )
            it.setLargeIcon(myBitmap)
        }.build()
    }

    private fun buttonsListeners() {
        val notificationManager = NotificationManagerCompat.from(this)

        binding.btnNotification1.setOnClickListener {
            notificationManager.notify(notificationOneId, notificationOne)
        }

        binding.btnNotification2.setOnClickListener {
            notificationManager.notify(notificationTwoId, notificationTwo)
        }

        binding.btnNotification3.setOnClickListener {
            notificationManager.notify(notificationThreeId, notificationThree)
        }

        binding.btnNotification4.setOnClickListener {
            notificationManager.notify(notificationFourId, notificationFour)
        }

        binding.btnNotification5.setOnClickListener {
            notificationManager.notify(notificationFiveId, notificationFive)
        }

        binding.btnCustomNotification.setOnClickListener {
            notificationManager.notify(customNotificationId, customNotification)
        }
    }

    private fun createNotificationChannel() {
        val channelImportance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(channelID, channelName, channelImportance).apply {
            lightColor = Color.RED
            enableLights(true)
        }

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}