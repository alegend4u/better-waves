from django.db import models

MAX_CHAR = 63


class Song(models.Model):
    title = models.CharField(max_length=MAX_CHAR)
    file = models.FileField(upload_to="music", blank=True, null=True)
    genre = models.CharField(max_length=MAX_CHAR)
    album = models.ForeignKey('Album', related_name='song_album', on_delete=models.CASCADE, blank=True, null=True)

    def __str__(self):
        return self.title


class Album(models.Model):
    title = models.CharField(max_length=MAX_CHAR)

    def __str__(self):
        return self.title
