from rest_framework import serializers
from music.models import *


class SongSerializer(serializers.HyperlinkedModelSerializer):
    album_title = serializers.SerializerMethodField('get_album_title')
    artist_title = serializers.SerializerMethodField('get_artist_title')
    album = serializers.HyperlinkedIdentityField(view_name='song-album', read_only=True)
    artist = serializers.HyperlinkedIdentityField(view_name='song-artist', read_only=True)

    class Meta:
        model = Song
        fields = ('title', 'album', 'artist', 'album_title', 'artist_title', 'genre', 'url')

    def get_album_title(self, song):
        return song.album.title

    def get_artist_title(self, song):
        return song.album.artist.title


class AlbumSerializer(serializers.HyperlinkedModelSerializer):
    artist = serializers.HyperlinkedRelatedField(view_name='artist-detail', read_only=True)

    class Meta:
        model = Album
        fields = '__all__'


class ArtistSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = Artist
        fields = '__all__'


class UserSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = User
        fields = ['username']
