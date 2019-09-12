from rest_framework import serializers
from music.models import *


class SongSerializer(serializers.HyperlinkedModelSerializer):
    album = serializers.HyperlinkedIdentityField(view_name='album-detail', read_only=True)

    class Meta:
        model = Song
        fields = '__all__'


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
