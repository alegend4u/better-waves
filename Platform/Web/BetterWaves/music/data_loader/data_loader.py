import os
import random
import pandas as pd
from random_username.generate import generate_username

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "BetterWaves.settings")
import django
django.setup()
from music import models

data_file = 'data.csv'

songs = pd.read_csv(data_file)

# ============== Create random users ==============
# user_list = list(songs['user_id'].unique())[:2001]
# for user_id in user_list:
#     username, password = generate_username(2)
#     user = models.User(username=username, password=password)
#     get_users = models.User.objects.filter(id=user_id)
#     if not get_users:
#         user.id = user_id
#         # print(user.id, user.username, user.password)
#         user.save()

# ============== Create random users ==============
RATING = [-1, 1, 2, 3, 4, 5]

for index,row in songs.iterrows():
    # print(row['user_id'], row['song_id'], row['listen_count'])
    user_id = row['user_id']
    song_id = row['song_id']
    listen_count = row['listen_count']
    rating = random.choice(RATING)

    user = models.User.objects.get(id=user_id)
    song = models.Song.objects.get(id=song_id)
    if not models.UserSong.objects.filter(user_of_song=user, song=song):
        user_song = models.UserSong(user_of_song=user, song=song, listen_count=listen_count, rating=rating)
        # print(user, song, listen_count, rating)
        user_song.save()
