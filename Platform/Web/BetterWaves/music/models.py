from django.db import models
from django.contrib.auth.models import AbstractUser
from music.storage import OverwriteStorage
from rest_framework.authtoken.models import Token

MAX_CHAR = 63


class User(AbstractUser):
    rated_songs = models.ManyToManyField('Song')


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
    listen_count = models.IntegerField()
    song = models.ForeignKey('Song', on_delete=models.CASCADE)
    user = models.ForeignKey('User', on_delete=models.CASCADE)


class Artist(models.Model):
    title = models.CharField(max_length=MAX_CHAR, default='Unknown Artist')

    def __str__(self):
        return self.title


class Album(models.Model):
    artist = models.ForeignKey('Artist', related_name="album_artist", on_delete=models.CASCADE, null=True, blank=True)
    title = models.CharField(max_length=MAX_CHAR, null=True, blank=True)

    def __str__(self):
        return self.title
