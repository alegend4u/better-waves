from django import forms
from music.models import *
from django.contrib import admin


class UserForm(forms.ModelForm):
    class Meta:
        model = User

    songs = forms.ModelMultipleChoiceField(queryset=Song.objects.all())

    def __init__(self, *args, **kwargs):
        super(UserForm, self).__init__(*args, **kwargs)
        if self.instance:
            self.fields['songs'].initial = self.instance.songs.get_queryset()

    def save(self, *args, **kwargs):
        instance = super(UserForm, self).save(commit=False)
        self.fields['songs'].initial.update(user=None)
        self.cleaned_data['songs'].update(user=instance)
        return instance
