from django.contrib import admin
from django.conf.urls import url, include

from django.conf import settings
from django.conf.urls.static import static

from music import views
from music import library_loader

urlpatterns = [
    url('admin/', admin.site.urls),
    url('', include('music.urls')),
    url('play', views.player)
]

urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)

# Startup Load (Disable when migrating)
library_loader.load_library()
