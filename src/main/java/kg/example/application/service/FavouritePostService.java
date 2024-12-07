package kg.example.application.service;

import kg.example.application.dto.ShortFavouritePostDto;

public interface FavouritePostService {
    ShortFavouritePostDto addToFavourites(Long postId);
    ShortFavouritePostDto removeFromFavourites(Long postId);
}
