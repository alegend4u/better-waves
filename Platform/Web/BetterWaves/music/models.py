from django.db import models
from django.contrib.auth.models import AbstractUser
from music.storage import OverwriteStorage

MAX_CHAR = 63


class User(AbstractUser):
    songs = models.ManyToManyField('Song')


class Song(models.Model):
    title = models.CharField(max_length=MAX_CHAR)
    file = models.FileField(upload_to="music", storage=OverwriteStorage(), blank=True, null=True)

    ROCK = 'rock'
    POP = 'pop'
    JAZZ = 'jazz'

    GENRE_CHOICES = (
        (ROCK, 'Rock'),
        (POP, 'Pop'),
        (JAZZ, 'Jazz')
    )

    genre = models.CharField(max_length=MAX_CHAR, choices=GENRE_CHOICES)
    album = models.ForeignKey('Album', related_name='song_album', on_delete=models.CASCADE, blank=True, null=True)

    def __str__(self):
        return self.title


class UserSong(models.Model):
    listen_count = models.IntegerField()
    song = models.ForeignKey('Song', on_delete=models.CASCADE)
    user = models.ForeignKey('User', on_delete=models.CASCADE)


class Artist(models.Model):
    title = models.CharField(max_length=MAX_CHAR)

    def __str__(self):
        return self.title


class Album(models.Model):
    artist = models.ForeignKey('Artist', related_name="album_artist", on_delete=models.CASCADE)
    title = models.CharField(max_length=MAX_CHAR)

    def __str__(self):
        return self.title
