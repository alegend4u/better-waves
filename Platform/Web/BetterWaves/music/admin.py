from django.contrib import admin
from django.contrib.admin import ModelAdmin

from music.models import *
from django.contrib.auth import get_user_model
# Register your models here.
# from django.contrib.auth.admin import UserAdmin

admin.site.register(get_user_model())
admin.site.register(Song)
admin.site.register(Album)
admin.site.register(Artist)


class UserSongAdmin(ModelAdmin):
    list_display = ['username', 'title', 'listen_count', 'rating']

    def username(self, user_song):
        return user_song.user_of_song.username

    def title(self, user_song):
        return user_song.song.title



admin.site.register(UserSong, UserSongAdmin)
