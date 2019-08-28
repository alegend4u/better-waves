from django.shortcuts import render
from django.http import StreamingHttpResponse, HttpResponse
from rest_framework import generics
from rest_framework.response import Response
from rest_framework.views import APIView
from music.serializers import *
from music.models import *


class SongList(generics.ListCreateAPIView):
    queryset = Song.objects.all()
    serializer_class = SongSerializer


class SongDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Song.objects.all()
    serializer_class = SongSerializer


class AlbumList(generics.ListCreateAPIView):
    queryset = Album.objects.all()
    serializer_class = AlbumSerializer


class AlbumDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Album.objects.all()
    serializer_class = AlbumSerializer


class AlbumSongs(generics.ListCreateAPIView):
    queryset = Album.objects.all()
    serializer_class = AlbumSerializer

    def get(self, request, *args, **kwargs):
        album = self.get_object()
        songs = Song.objects.filter(album=album)
        serializer = SongSerializer(songs, context={'request': request}, many=True)
        return Response(serializer.data)


class SongAlbum(generics.RetrieveAPIView):
    queryset = Song.objects.all()
    serializer_class = SongSerializer

    def get(self, request, *args, **kwargs):
        song = self.get_object()
        album = song.album
        serializer = AlbumSerializer(album, context={'request': request})
        return Response(serializer.data)


def stream(request, pk, format=None):
    song = Song.objects.get(pk=pk)
    data = song.file.open('rb')
    response = HttpResponse(data, content_type='audio/mp3')
    response['Content-Disposition'] = 'inline; filename="stream.mp3"'
    response['Accept-Ranges'] = 'bytes'
    print('My headers: ', request.headers)
    return response


def player(request):
    return render(request, 'music/player.html')
