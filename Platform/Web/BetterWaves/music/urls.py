from django.conf.urls import url, include
from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns
from music import views


urlpatterns = [
    path('songs', views.SongList.as_view(), name='song-list'),
    path('albums', views.AlbumList.as_view(), name='album-list'),
    path('song/<int:pk>', views.SongDetail.as_view(), name='song-detail'),
    path('album/<int:pk>', views.AlbumDetail.as_view(), name='album-detail'),
    path('album/<int:pk>/songs', views.AlbumSongs.as_view(), name='album-songs'),
    path('song/<int:pk>/album', views.SongAlbum.as_view(), name='song-album'),
    path('stream/<int:pk>', views.stream, name='song-stream'),
]

urlpatterns = format_suffix_patterns(urlpatterns)
