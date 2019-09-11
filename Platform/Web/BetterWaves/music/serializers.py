from rest_framework import serializers
from music.models import *


class SongSerializer(serializers.HyperlinkedModelSerializer):
    album = serializers.HyperlinkedIdentityField(view_name='album-detail', read_only=True)

    class Meta:
        model = Song
        fields = '__all__'


class AlbumSerializer(serializers.HyperlinkedModelSerializer):
    # song = serializers.HyperlinkedRelatedField(many=True, view_name='song-detail', read_only=True)

    class Meta:
        model = Album
        fields = '__all__'


class UserSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = User
        fields = '__all__'


# class StreamSerializer(serializers.Serializer):
#     bytes = serializers.FileField()
