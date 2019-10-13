from django.shortcuts import render
from django.http import HttpResponse, Http404
from rest_framework import generics
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.authentication import TokenAuthentication
from music.serializers import *
from music.models import *
from music.recom_engine import RecommendationEngine


engine = RecommendationEngine()


class TokenMixin(object):
    authentication_classes = [TokenAuthentication, ]
    permission_classes = [IsAuthenticated, ]


class SongList(generics.ListCreateAPIView, TokenMixin):
    queryset = Song.objects.all()
    serializer_class = SongSerializer


class SongDetail(generics.RetrieveUpdateDestroyAPIView, TokenMixin):
    queryset = Song.objects.all()
    serializer_class = SongSerializer


class SongAlbum(generics.RetrieveAPIView, TokenMixin):
    queryset = Song.objects.all()

    def get(self, request, *args, **kwargs):
        song = self.get_object()
        album = song.album

        serializer = AlbumSerializer(album, context={'request': request})
        return Response(serializer.data)


class SongArtist(APIView, TokenMixin):

    def get_object(self, pk):
        try:
            return Song.objects.get(pk=pk)
        except Song.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        song = self.get_object(pk)
        artist = song.album.artist

        serializer = ArtistSerializer(artist, context={'request': request})
        return Response(serializer.data)


class AlbumList(generics.ListCreateAPIView, TokenMixin):
    queryset = Album.objects.all()
    serializer_class = AlbumSerializer


class AlbumDetail(generics.RetrieveUpdateDestroyAPIView, TokenMixin):
    queryset = Album.objects.all()
    serializer_class = AlbumSerializer


class AlbumSongs(generics.ListCreateAPIView, TokenMixin):
    serializer_class = AlbumSerializer
    queryset = Album.objects.all()

    def get(self, request, *args, **kwargs):
        album = self.get_object()
        songs = Song.objects.filter(album=album)

        serializer = SongSerializer(songs, context={'request': request}, many=True)
        return Response(serializer.data)


class AlbumArtist(APIView, TokenMixin):

    def get_object(self, pk):
        try:
            return Album.objects.get(pk=pk)
        except Album.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        album = self.get_object(pk=pk)
        artist = album.artist
        serializer = ArtistSerializer(artist, context={'request': request}, many=True)
        return Response(serializer.data)


class ArtistList(generics.ListCreateAPIView, TokenMixin):
    queryset = Artist.objects.all()
    serializer_class = ArtistSerializer


class ArtistDetail(generics.RetrieveUpdateDestroyAPIView, TokenMixin):
    queryset = Artist.objects.all()
    serializer_class = ArtistSerializer


class ArtistAlbums(APIView, TokenMixin):

    def get_object(self, pk):
        try:
            return Artist.objects.get(pk=pk)
        except Artist.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        artist = self.get_object(pk=pk)
        albums = Album.objects.filter(artist=artist)
        serializer = AlbumSerializer(albums, context={'request': request}, many=True)
        return Response(serializer.data)


class UserList(generics.ListCreateAPIView, TokenMixin):
    queryset = User.objects.all()
    serializer_class = UserSerializer


class UserDetail(generics.RetrieveUpdateDestroyAPIView, TokenMixin):
    queryset = User.objects.all()
    serializer_class = UserSerializer


class UserSongs(APIView, TokenMixin):

    def get_object(self, pk):
        try:
            user = User.objects.get(pk=pk)
        except User.DoesNotExist:
            raise Http404
        return user

    def get(self, request, pk, format=None):
        user = self.get_object(pk=pk)
        songs = user.songs
        serializer = SongSerializer(songs, context={'request': request}, many=True)
        return Response(serializer.data)


class GenreSongs(APIView, TokenMixin):

    def get_object(self, gid):
        from eyed3.id3 import Genre
        genre = Genre(id=gid)
        songs = Song.objects.filter(genre=genre.name)
        return songs

    def get(self, request, gid, format=None):
        songs = self.get_object(gid)
        serializer = SongSerializer(songs, context={'request': request}, many=True)
        return Response(serializer.data)


class Stream(APIView, TokenMixin):

    def get(self, request, pk, format=None):
        song = Song.objects.get(pk=pk)
        print("Reading path:", song.file)
        data = song.file.open('rb')
        response = HttpResponse(data, content_type='audio/mp3')
        response['Content-Disposition'] = 'inline; filename="stream.mp3"'
        response['Accept-Ranges'] = 'bytes'

        # Increase play count
        user = request.user
        user_song = UserSong.objects.get_or_create(user_of_song=user, song=song)[0]
        user_song.listen_count += 1
        user_song.save()

        return response


class Recommend(APIView, TokenMixin):

    def get(self, request, format=None):
        user = request.user
        songs = engine.recommend(user.id)
        serializer = SongSerializer(songs, context={'request': request}, many=True)

        return Response(serializer.data)


def player(request):
    return render(request, 'music/player.html')
