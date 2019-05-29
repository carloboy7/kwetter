package ui.handlers;

import backend.BackendService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchPageTest {

    @Mock
    BackendService backendService;

    @InjectMocks
    private SearchPage searchPage;

    @Test
    void SearchSearchesForUserAndKweet() throws IOException, URISyntaxException {
        searchPage.setQuery("query");
        searchPage.search();

        verify(backendService).searchKweets(ArgumentMatchers.eq("query"));
        verify(backendService).searchUsers(ArgumentMatchers.eq("query"));
        verifyNoMoreInteractions(backendService);
    }

    @Test
    void DeleteKweet() throws IOException, URISyntaxException {
        searchPage.setQuery("query");
        searchPage.delete(1);

        verify(backendService).deleteKweet(1);
        verify(backendService, times(1)).searchKweets("query");

    }

}
