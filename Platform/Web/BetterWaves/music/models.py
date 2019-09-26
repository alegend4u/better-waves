from django.db import models
from django.contrib.auth.models import AbstractUser
from music.storage import OverwriteStorage
from rest_framework.authtoken.models import Token

MAX_CHAR = 63


class User(AbstractUser):
    rated_songs = models.ManyToManyField('Song', through='UserSong')


class Song(models.Model):
    title = models.CharField(max_length=MAX_CHAR)
    file = models.FileField(upload_to="music", storage=OverwriteStorage(), blank=True, null=True)

    Pop = 'Pop'
    Rock = 'Rock'
    Electronic = 'Electronic'
    Soundtrack = 'Soundtrack'
    HipHop = 'Hip-Hop'
    Club = 'Club'
    Classical = 'Classical'
    RB = 'R&B'
    Blues = 'Blues'
    Metal = 'Metal'

    GENRE_CHOICES = (
        (Pop, 'Pop'),
        (Rock, 'Rock'),
        (Electronic, 'Electronic'),
        (Soundtrack, 'Soundtrack'),
        (HipHop, 'Hip-Hop'),
        (Club, 'Club'),
        (Classical, 'Classical'),
        (RB, 'R&B'),
        (Blues, 'Blues'),
        (Metal, 'Metal')
    )

    genre = models.CharField(max_length=MAX_CHAR, choices=GENRE_CHOICES, null=True, blank=True)
    album = models.ForeignKey('Album', related_name='song_album', on_delete=models.CASCADE, blank=True, null=True)

    def __str__(self):
        return self.title


class UserSong(models.Model):
    song = models.ForeignKey(Song, on_delete=models.CASCADE)
    user_of_song = models.ForeignKey(User, on_delete=models.CASCADE, null=True)

    listen_count = models.IntegerField(default=0)
    rating = models.IntegerField(default=-1)  # Out of 5. -1 means not rated

    def __str__(self):
        return self.user_of_song.username + " - " + self.song.title


class Artist(models.Model):
    title = models.CharField(max_length=MAX_CHAR, default='Unknown Artist')

    def __str__(self):
        return self.title


class Album(models.Model):
    artist = models.ForeignKey('Artist', related_name="album_artist", on_delete=models.CASCADE, null=True, blank=True)
    title = models.CharField(max_length=MAX_CHAR, null=True, blank=True)

    def __str__(self):
        return self.title
