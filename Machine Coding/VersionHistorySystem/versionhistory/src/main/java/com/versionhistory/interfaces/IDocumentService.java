package com.versionhistory.interfaces;

import java.util.List;

import com.versionhistory.constants.AccessLevel;
import com.versionhistory.document.DocumentVersion;
import com.versionhistory.dto.DocumentDTO;
import com.versionhistory.exceptions.AuthorizationException;
import com.versionhistory.exceptions.DocumentVersionException;
import com.versionhistory.exceptions.NotFoundException;
import com.versionhistory.exceptions.ValidationException;
import com.versionhistory.user.User;

public interface IDocumentService {

    String createDocument(User user, String title, String content) throws ValidationException;

    void updateDocument(User user, String documentId, String title, String content) throws AuthorizationException, NotFoundException, ValidationException, DocumentVersionException;

    void changeDocumentVersion(User user, String documentId, Integer version)
            throws AuthorizationException, NotFoundException, DocumentVersionException;

    void deleteDocument(User user, String documentId) throws AuthorizationException, NotFoundException;

    DocumentDTO getDocument(User user, String documentId) throws AuthorizationException, NotFoundException;

    void setUserAccess(User owner, String documentId, User recipient, AccessLevel accessLevel) throws AuthorizationException, NotFoundException;

    void unsetUserAccess(User owner, String documentId, User recipient) throws AuthorizationException, NotFoundException;

    List<DocumentVersion> listVersions(User user, String documentId) throws AuthorizationException, NotFoundException;

    List<DocumentVersion> listVersions(User user, String documentId, int offset, int limit) throws AuthorizationException, NotFoundException, ValidationException;

    DocumentVersion getVersion(User user, String documentId, Integer version) throws AuthorizationException, NotFoundException, com.versionhistory.exceptions.DocumentVersionException;

    void revertToVersion(User user, String documentId, Integer version) throws AuthorizationException, NotFoundException, com.versionhistory.exceptions.DocumentVersionException;
}