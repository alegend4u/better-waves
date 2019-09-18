from django.shortcuts import render
from django.http import HttpResponse, Http404
from rest_framework import generics
from rest_framework.response import Response
from rest_framework.views import APIView
from music.serializers import *
from music.models import *


# class SongList(APIView):
#
#     def get_object(self):
#         try:
#             return Song.objects.all()
#         except Song.DoesNotExist:
#             raise Http404
#
#     def get(self, request, format=None):
#         songs = self.get_object()
#
#         serializer = serializers.SongSerializer(songs, context={'request': request})

class SongList(generics.ListCreateAPIView):
    queryset = Song.objects.all()
    serializer_class = SongSerializer


class SongDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Song.objects.all()
    serializer_class = SongSerializer


class SongAlbum(generics.RetrieveAPIView):
    queryset = Song.objects.all()

    def get(self, request, *args, **kwargs):
        song = self.get_object()
        album = song.album

        serializer = AlbumSerializer(album, context={'request': request})
        return Response(serializer.data)


class SongArtist(APIView):

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


class AlbumList(generics.ListCreateAPIView):
    queryset = Album.objects.all()
    serializer_class = AlbumSerializer


class AlbumDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Album.objects.all()
    serializer_class = AlbumSerializer


class AlbumSongs(generics.ListCreateAPIView):
    serializer_class = AlbumSerializer
    queryset = Album.objects.all()

    def get(self, request, *args, **kwargs):
        album = self.get_object()
        songs = Song.objects.filter(album=album)

        serializer = SongSerializer(songs, context={'request': request}, many=True)
        return Response(serializer.data)


class AlbumArtist(APIView):

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




class ArtistList(generics.ListCreateAPIView):
    queryset = Artist.objects.all()
    serializer_class = ArtistSerializer


class ArtistDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Artist.objects.all()
    serializer_class = ArtistSerializer


class ArtistAlbums(APIView):

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


class UserList(generics.ListCreateAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer


class UserDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer


class UserSongs(APIView):

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


class GenreSongs(APIView):

    def get_object(self, gid):
        from eyed3.id3 import Genre
        genre = Genre(id=gid)
        songs = Song.objects.filter(genre=genre.name)
        return songs

    def get(self, request, gid, format=None):
        songs = self.get_object(gid)
        serializer = SongSerializer(songs, context={'request': request}, many=True)
        return Response(serializer.data)


def stream(request, pk, format=None):
    song = Song.objects.get(pk=pk)
    data = song.file.open('rb')
    response = HttpResponse(data, content_type='audio/mp3')
    response['Content-Disposition'] = 'inline; filename="stream.mp3"'
    response['Accept-Ranges'] = 'bytes'
    return response


def player(request):
    return render(request, 'music/player.html')
