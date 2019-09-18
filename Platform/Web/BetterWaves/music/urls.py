from django.conf.urls import url, include
from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns
from music import views


urlpatterns = [
    # songs urls
    path('songs', views.SongList.as_view(), name='song-list'),
    path('song/<int:pk>', views.SongDetail.as_view(), name='song-detail'),
    path('song/<int:pk>/album', views.SongAlbum.as_view(), name='song-album'),
    path('song/<int:pk>/artist', views.SongArtist.as_view(), name='song-artist'),

    # albums urls
    path('albums', views.AlbumList.as_view(), name='album-list'),
    path('album/<int:pk>', views.AlbumDetail.as_view(), name='album-detail'),
    path('album/<int:pk>/songs', views.AlbumSongs.as_view(), name='album-songs'),
    path('album/<int:pk>/artist', views.AlbumArtist.as_view(), name='album-artist'),

    # artists urls
    path('artists', views.ArtistList.as_view(), name='artist-list'),
    path('artist/<int:pk>', views.ArtistDetail.as_view(), name='artist-detail'),
    path('artist/<int:pk>/albums', views.ArtistAlbums.as_view(), name='artist-albums'),

    # users urls
    path('users/', views.UserList.as_view(), name='user-list'),
    path('user/<int:pk>', views.UserDetail.as_view(), name='user-detail'),
    path('user/<int:pk>/songs', views.UserSongs.as_view(), name='user-songs'),

    # genre urls
    path('genre/<int:gid>/songs', views.GenreSongs.as_view(), name='genre=songs'),

    # stream urls
    path('stream/<int:pk>', views.stream, name='song-stream'),
]

urlpatterns = format_suffix_patterns(urlpatterns)
