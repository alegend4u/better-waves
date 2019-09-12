from django.contrib import admin
from music.models import *
from django.contrib.auth import get_user_model
# Register your models here.
# from django.contrib.auth.admin import UserAdmin

admin.site.register(get_user_model())
admin.site.register(Song)
admin.site.register(Album)
admin.site.register(Artist)
