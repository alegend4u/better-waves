# This file is purely based on the mp3 files preprocess to make file attributes relevant
import eyed3
import os
import random
from eyed3.id3 import Genre
from pathlib import Path

GENRE_LIST = [
    'Pop',
    'Rock',
    'Electronic',
    'Soundtrack',
    'Hip-Hop',
    'Club',
    'Classical',
    'R&B',
    'Blues',
    'Metal'
]
genres = []
for i in GENRE_LIST:
    genres.append(Genre(i))

entries = Path(os.path.join(os.getcwd(), 'music'))
files = []
for entry in entries.iterdir():
    files.append(entry)

for file in files:
    filepath = os.path.join(os.getcwd(), 'music',file.name)
    audiofile = eyed3.load(filepath)

    #Get random genre from list
    selected_genre = random.choice(genres)
    # print(selected_genre)
    audiofile.tag.genre = selected_genre
    audiofile.tag.save()
