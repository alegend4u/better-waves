from django.shortcuts import render
from django.http import Http404
from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response
from music.models import Song
from music.serializers import SongSerializer


class SongList(APIView):
    def get(self, request, format=None):
        serializer = SongSerializer(Song.objects.all(), many=True, context={'request': request})
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = SongSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class SongDetail(APIView):
    """
    Retrieve, update or delete a code song.
    """
    def get_object(self, pk):
        try:
            return Song.objects.get(pk=pk)
        except Song.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

    def get(self, request, pk, format=None):
        serializer = SongSerializer(self.get_object(pk))
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        serializer = SongSerializer(self.get_object(pk), data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        song = self.get_object(pk)
        song.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
