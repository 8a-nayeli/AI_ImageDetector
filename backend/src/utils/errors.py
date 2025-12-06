class BackendError(Exception):
    """Base exception for backend failures."""


class NotFoundError(BackendError):
    """Raised when an entity cannot be found."""


class ValidationError(BackendError):
    """Raised for domain validation problems."""


